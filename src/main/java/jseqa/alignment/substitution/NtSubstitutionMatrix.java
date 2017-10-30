package jseqa.alignment.substitution;

@Deprecated
public abstract class NtSubstitutionMatrix extends AbstractSubstitutionMatrix {

	public NtSubstitutionMatrix() {
		super();
	}
	
	public NtSubstitutionMatrix(int gap) {
		this(gap, gap);
	}
	
	public NtSubstitutionMatrix(int gap, int ext) {
		super(gap, ext);
	}
	
	protected int ntIndex(char ch) {
		if(ch == 'A') { 
			return 0;
		} else if(ch == 'C') { 
			return 1;
		} else if(ch == 'G') { 
			return 2;
		} else if(ch == 'T') {
			return 3;
		} else if(ch == 'N') {
			return 4;
		} else {
			return 5;
		}
	}

}
