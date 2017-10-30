package jseqa.fastq;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.zip.GZIPInputStream;

/**
 * Base class for reading sequence files.
 *
 */
public abstract class AbstractSequenceReader implements AutoCloseable {
	
	/** Sequence file to read. */
	protected Path file;
	
	/** flag if file is gzipped or not. */
	protected boolean isGz;
	
	/** buffered reader of file. */
	protected BufferedReader buff;
	
	AbstractSequenceReader(Path seqFile) throws FileNotFoundException {
		this.file = seqFile;
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.gz");
		this.isGz = matcher.matches(this.file.getFileName());
		this.open();
	}
	
	AbstractSequenceReader(File seqFile) throws FileNotFoundException {
		this(seqFile.toPath());
	}
	
	/**
	 * Opens the file, called by constructor.
	 * @throws FileNotFoundException if {@link #file} is not found.
	 */
	protected void open() throws FileNotFoundException {
		try {
			if(this.isGz) {
				FileInputStream fis = new FileInputStream(this.file.toFile());
				GZIPInputStream gz = new GZIPInputStream(fis);
				InputStreamReader isr = new InputStreamReader(gz);
				this.buff = new BufferedReader(isr);
			} else {
				FileReader fr = new FileReader(this.file.toFile());
				this.buff = new BufferedReader(fr);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close the file reader.
	 */
	public void close() throws IOException {
		this.buff.close();
	}
	
}
