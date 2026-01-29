# Variables
JC = javac
JVM = java
BIN = bin
SRC = src/main/java
MAIN = Egg

# Default target: Compile everything
# The wildcard finds all .java files in the src directory
all:
	@mkdir -p $(BIN)
	$(JC) -d $(BIN) $(SRC)/**/*.java

# Run the application
# -cp (classpath) tells Java where to look for the compiled classes
run: all
	$(JVM) -cp $(BIN) $(MAIN)

# Clean up the build directory
clean:
	rm -rf $(BIN)

# Rebuild from scratch
rebuild: clean all
