package com.org.api;


class Crate<T> {
	private T contents;
	public T emptyCrate() {
		return contents;
	}
	public void packCrate(T contents) {
		this.contents = contents;
	}
	@Override
	public String toString() {
		return String.format("Crate [contents=%s]", contents);
	}
	
	

}
public class TestApp {

	
	public static void main(String[] args) {
		Crate<String> crate =  new Crate<String>();
		String str = "Harshad";
		crate.packCrate("hello");

		System.out.println("crate :: " + crate);
		System.out.println("::::::: "+str.equals(null));
		
	}

}
