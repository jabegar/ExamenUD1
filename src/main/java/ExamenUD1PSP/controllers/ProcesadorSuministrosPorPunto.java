package ExamenUD1PSP.controllers;

import java.util.ArrayList;
import java.util.List;

public class ProcesadorSuministrosPorPunto {
	public static void main(String[] args) {
		ProcesadorSuministrosPorPunto pspp = new ProcesadorSuministrosPorPunto();
		pspp.calcularConsumo(args[0].split(" "));
	}
	  
	public void calcularConsumo(String args[]) {
		String identificador = args[0];
		
		List<Integer> recargas = new ArrayList<Integer>();
		
		for (int i = 1; i < args.length; i++) {
			int recarga = Integer.parseInt(args[i]);
			recargas.add(recarga);
		}
		
		int kwTotales = 0;
		double importeTotal = 0.0;
		
		for (Integer recarga : recargas) {
			kwTotales += recarga;
			
			importeTotal += recarga * 0.3;
		}
		
		System.out.println(identificador + ";" + kwTotales + ";" + importeTotal);
	}
}
