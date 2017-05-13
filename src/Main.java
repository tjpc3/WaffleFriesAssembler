import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Main {
	
	public static String[] instructions = {"nop", "mov", "lda", "add", "sub", "jmp", "cmp", "rand", "js", "out", "nop", "nop", "nop", "nop", "nop", "hlt"};
	public static String[] inputs = {"bin1", "bin2", "axe", "bxe", "cxe", "dxe"};
	public static String[] accumulators = {"ax", "bx", "cx", "dx"};
	
	public static void main(String args[]) {
		Stream inputFile;
		try {
			Path inputFilePath = FileSystems.getDefault().getPath(args[0]);
			inputFile = Files.lines(inputFilePath);
		} catch (IOException e) {
			System.err.println("Error reading file.");
			return;
		}
		
		PrintWriter outputFile;
		
		try {
		    outputFile = new PrintWriter(args[1], "UTF-8");
		} catch (IOException e) {
		   System.err.println("Unable to create new file.");
		   return;
		}
		
		Consumer<String> translate = (line) -> {
			for (int i = 0; i < instructions.length; i++) {
				line = line.replace(instructions[i], String.format("%4s", Integer.toBinaryString(i)).replace(' ', '0'));
			}
			
			for (int i = 0; i < inputs.length; i++) {
				line = line.replace(inputs[i], String.format("%3s", Integer.toBinaryString(i)).replace(' ', '0'));
			}
			
			for (int i = 0; i < accumulators.length; i++) {
				line = line.replace(accumulators[i], String.format("%2s", Integer.toBinaryString(i)).replace(' ', '0'));
			}
			
			outputFile.println(line);
		};
		
		inputFile.forEach(translate);
		
		outputFile.close();
		
		System.out.println("Compiled successfully.");
	}
	
}
