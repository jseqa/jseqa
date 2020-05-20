/**
 * Classes for parsing FASTA and FASTQ files.
 * <br><br>
 * There are two types of classes: full parsers (e.g., {@link jseqa.fastq.FastaReader}, {@link jseqa.fastq.FastqReader}),
 * and interval parsers that take an interval entries {@link jseqa.fastq.IntervalFastaReader}, {@link jseqa.fastq.IntervalFastqReader}.
 * <br><br>
 * Additionally, two FASTQ files can be read simultaneously using {@link jseqa.fastq.PairedFastqReader}, e.g., to
 * read related entries in R1/R2 FASTQ files from paired-end reads.
 */
package jseqa.fastq;