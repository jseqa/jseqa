package jseqa.fastq;

/**
 * Entry containing a pair of {@code QualityEntry} objects.
 *
 */
public class PairedEntry {
	
	final private QualityEntry ent1;
	
	final private QualityEntry ent2;
	
	public PairedEntry(QualityEntry e1, QualityEntry e2) {
		this.ent1 = e1;
		this.ent2 = e2;
	}
	
	public QualityEntry firstEntry() {
		return this.ent1;
	}
	
	public QualityEntry secondEntry() {
		return this.ent2;
	}
}
