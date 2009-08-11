all:	compile

compile:
	/opt/scala/bin/scalac -classpath . -sourcepath . *.scala

test:
	/opt/scala/bin/scala -classpath . App ~/cg/lookat

test2:
	/opt/scala/bin/scala -classpath . App ~/cg/_cobbler

test3:
	/opt/scala/bin/scala -classpath . App ~/cg/func

clean:
	rm *.class

