TARGET_JAR=target/uberjar/icfp2015-0.1.0-SNAPSHOT-standalone.jar

$(TARGET_JAR): src/icfp2015/*.clj
	lein uberjar

.PHONY: clean
clean:
	rm -f $(TARGET_JAR)
