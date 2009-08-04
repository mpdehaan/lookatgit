all:	compile

compile:
	/opt/scala/bin/scalac -classpath . -sourcepath . *.scala

test:
	/opt/scala/bin/scala -classpath . App ~/cg/_cobbler

clean:
	rm *.class

