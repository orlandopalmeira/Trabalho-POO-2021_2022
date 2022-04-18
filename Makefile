main:
	javac ./codigo_fonte/*.java

execute:
	javac ./codigo_fonte/*.java
	mv ./codigo_fonte/Main.class .
	java Main
	rm Main.class ./codigo_fonte/*.class


clean:
	rm ./codigo_fonte/*.class