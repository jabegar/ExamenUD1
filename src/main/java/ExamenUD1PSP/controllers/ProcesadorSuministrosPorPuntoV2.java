package ExamenUD1PSP.controllers;

import java.util.ArrayList;
import java.util.List;

public class ProcesadorSuministrosPorPuntoV2 {
	public static void main(String[] args) {
		ProcesadorSuministrosPorPuntoV2 pspp = new ProcesadorSuministrosPorPuntoV2();
		pspp.calcularConsumo(args[0].split(" "));
	}
	  
	public void calcularConsumo(String args[]) {
		String identificador = args[0];
		
		List<Integer> recargas = new ArrayList<Integer>();
		
		int nRecargas = Integer.parseInt(args[1]);
		
		if (nRecargas != args.length-2) {
			System.err.println("Para el punto: " + identificador + " el nº de lecturas recibidas no se corresponde con el total.");
			
			System.exit(2);
		}
		
		for (int i = 2; i < args.length; i++) {
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
