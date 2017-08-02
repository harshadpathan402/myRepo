import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import com.caucho.hessian.client.HessianProxyFactory;
import com.sapienter.jbilling.server.util.api.JbillingAPI;


public class FCHosted {
	
	
	private static HostnameVerifier allowAllHosts() {
		try {
		TrustManager[] trustAllCerts = new TrustManager[] {
				   new X509TrustManager() {
				      public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				        return null;
				      }
				      
					@Override
					public void checkClientTrusted(
							java.security.cert.X509Certificate[] chain,
							String authType) throws CertificateException {
						
					}

					@Override
					public void checkServerTrusted(
							java.security.cert.X509Certificate[] chain,
							String authType) throws CertificateException {
						
					}

				   }
				};

				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, trustAllCerts, new java.security.SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

				// Create all-trusting host name verifier
				HostnameVerifier allHostsValid = new HostnameVerifier() {
				    public boolean verify(String hostname, SSLSession session) {
				      return true;
				    }
				};
				
				return allHostsValid;
				
		} catch(Exception ex) {
			return null;
		}
	}
	
	private static final String FAILED_INVOICE_LOG = "/home/jbilling/Desktop/failedUsers61.txt";
	private static final String SUCCESS_INVOICE_LOG = "/home/jbilling/Desktop/successUsers61.txt";
    public static final int ORDER_CHANGE_STATUS_APPLY_ID = 3;
    
	public static void main(String[] args) throws IOException {
		
			allowAllHosts();
			System.out.println("Hessian client test ...");
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setUser("sysadmin;61");
			factory.setPassword("AtiOnTeTiVer#2017");
			final JbillingAPI hessianApiClient = (JbillingAPI) factory.create(JbillingAPI.class, 
					"https://fullcreative.jbilling.com/jbilling/hessian/ApiService");
			
			List<Integer> ListOfinoviceIds = hessianApiClient.getBillingProcessGeneratedInvoices(709);
			int processorCount = Runtime.getRuntime().availableProcessors();
			BigDecimal batch = BigDecimal.valueOf(ListOfinoviceIds.size()).divide(BigDecimal.valueOf(processorCount)).setScale(0, BigDecimal.ROUND_UP);
			List<List<Integer>> inoviceIds = chopped(ListOfinoviceIds, batch.intValue());
			List<Integer> successfulInvoiceList = Files.readAllLines(new File(SUCCESS_INVOICE_LOG).toPath())
													   .stream()
													   .filter(line -> !line.isEmpty())
													   .map(String::trim)
													   .map(Integer::valueOf)
													   .collect(Collectors.toList());
			
			ExecutorService service = Executors.newFixedThreadPool(processorCount);
			 
			for ( List<Integer> subList: inoviceIds) {
				service.execute(() -> {
					JbillingAPI api  = getJbillingAPIClient("sysadmin;61", "AtiOnTeTiVer#2017", "https://fullcreative.jbilling.com/jbilling/hessian/ApiService");
					for(Integer invoiceId : subList) {
						if(!successfulInvoiceList.contains(invoiceId)) {
							notify(invoiceId, api);
						}
					}
				});
				
			}
			// We need to wait to complete all submitted thread 
			//service.shutdown();
			System.out.println("\nDONE !!");

	}
	
	public  static void notify(Integer invoiceId, JbillingAPI api) {
		try {
			if(api.notifyInvoiceByEmail(invoiceId)) {
				Files.write(Paths.get(SUCCESS_INVOICE_LOG), Arrays.asList(invoiceId.toString()),  StandardCharsets.UTF_8,
						StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} else {
				Files.write(Paths.get(FAILED_INVOICE_LOG), Arrays.asList(invoiceId.toString()),  StandardCharsets.UTF_8,
						StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			}
		} catch (IOException ex) {
			
		}
	}
	
	public static JbillingAPI getJbillingAPIClient(String userName, String password, String url) {
		try {
			HessianProxyFactory factory = new HessianProxyFactory();
			factory.setUser(userName);
			factory.setPassword(password);
			return (JbillingAPI) factory.create(JbillingAPI.class, url);
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	static <T> List<List<T>> chopped(List<T> list, final int L) {
	    List<List<T>> parts = new ArrayList<>();
	    final int N = list.size();
	    for (int i = 0; i < N; i += L) {
	        parts.add(new ArrayList<T>(
	            list.subList(i, Math.min(N, i + L)))
	        );
	    }
	    return parts;
	}

}
