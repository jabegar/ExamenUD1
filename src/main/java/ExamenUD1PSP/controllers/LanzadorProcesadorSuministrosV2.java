package ExamenUD1PSP.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LanzadorProcesadorSuministrosV2 {
	public static void main(String[] args) {
		LanzadorProcesadorSuministrosV2 lps = new LanzadorProcesadorSuministrosV2();
		String rutaCompila = "src/main/java/ExamenUD1PSP/controllers/ProcesadorSuministrosPorPuntoV2.java";
		String rutaClass = "target/classes";
		String nombreClass = "ExamenUD1PSP.controllers.ProcesadorSuministrosPorPuntoV2";
		String rutaRecargas = "src/main/resources/recargas_v2.txt";
		
		try {
			lps.compilaProcesadorSuministros(rutaCompila, rutaClass, nombreClass);
			List<String> recargas = lps.leerFichero(rutaRecargas);
			
			lps.ejecutaProcesadorSuministros(rutaClass, nombreClass, recargas);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void compilaProcesadorSuministros(String rutaCompila, String rutaClass, String nombreClass) throws IOException, InterruptedException {
		File clase = new File(rutaClass + '/' + nombreClass.replace(".", "/"));
		
		if (!clase.exists()) {	
			ProcessBuilder pb = new ProcessBuilder("javac", "-d", rutaClass,  rutaCompila);
			Process p = pb.start();
			int exit = p.waitFor();
			
			System.out.println("Codigo de salida: " + exit);
		}
		else {
			System.out.println("El archivo ya esta compilado");
		}
	}
	
	public void ejecutaProcesadorSuministros(String ruta, String nombreClass, List<String> recargas) throws IOException, InterruptedException {
		List<ProcessBuilder> pbs = new ArrayList<ProcessBuilder>();
		for (String recarga : recargas) {
			String[] recargaSplit = recarga.split(";");
			String args = "";
			for (int i = 0; i < recargaSplit.length; i++) {
				args += recargaSplit[i] + " ";
			}
			String[] comando = {"java", "-cp", ruta, nombreClass, args};
			ProcessBuilder pb = new ProcessBuilder(comando);
			pbs.add(pb);
		}
		
		System.out.println("--- Resumen de facturación ---");
		double totalFacturado = 0.0;
		List<String> errores = new ArrayList<String>();
		for (ProcessBuilder pb : pbs) {
			pb.redirectErrorStream(true);
			Process p = pb.start();
			BufferedReader br;
			int exitValue = p.waitFor();
			
			if (exitValue == 0) {
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String linea = br.readLine();
				
				while(linea != null) {
					System.out.println(linea);
					float importe = Float.parseFloat((linea.split(";")[2]));
					
					totalFacturado += importe;
					
					linea = br.readLine();
				}
			}
			else {
				br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String linea = br.readLine();
				
				while(linea != null) {
					errores.add(linea);
					linea = br.readLine();
				}
			}
			
		}
		System.out.println("TOTAL facturado: " + totalFacturado + "€");
		System.out.println("--- Puntos de recarga con errores ---");
		for (String error : errores) {
			System.err.println(error);
		}
	}
	
	public List<String> leerFichero(String ruta) throws FileNotFoundException {
		List<String> recargas = new ArrayList<String>();
		
		File f = new File(ruta);
		FileReader fr = new FileReader(f);
		Scanner sc = new Scanner(fr);

		while(sc.hasNextLine()) {
			String palabra = sc.nextLine();
			recargas.add(palabra);
		}
		
		sc.close();
		return recargas;
	}
}
