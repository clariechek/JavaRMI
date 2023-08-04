# Compile everything
all: build runregistry server client clean

build: javac -d ./ Calculator.java CalculatorImplementation.java CalculatorServer.java CalculatorClient.java

runregistry: rmiregistry &

server: java -classpath ./ CalculatorServer &

client: java -classpath ./ CalculatorClient

clean: rm *.class