package xyz.marcelo.entity;

public class ConfigBuilder {

	private static StringBuilder stringBuilder = new StringBuilder();

	public static String buildJ48(Boolean U, Boolean O, Double C, Integer M, Boolean R, Integer N, Boolean B,
			Boolean S, Boolean L, Boolean A, Boolean J, Integer Q, Boolean doNotMakeSplitPointActualValue) {

		stringBuilder.setLength(0);

		stringBuilder.append(U ? " -U" : "");
		stringBuilder.append(O ? " -O" : "");
		stringBuilder.append(" -C " + (C != null ? C : "0.25"));
		stringBuilder.append(" -M " + (M != null ? M : "2"));
		stringBuilder.append(R ? " -R" : "");
		stringBuilder.append(" -N " + (N != null ? N : "3"));
		stringBuilder.append(B ? " -B" : "");
		stringBuilder.append(S ? " -S" : "");
		stringBuilder.append(L ? " -L" : "");
		stringBuilder.append(A ? " -A" : "");
		stringBuilder.append(J ? " -J" : "");
		stringBuilder.append(" -Q " + (Q != null ? Q : "1"));
		stringBuilder.append(" " + (doNotMakeSplitPointActualValue ? "-doNotMakeSplitPointActualValue" : ""));

		return stringBuilder.toString().trim();
	}

}
