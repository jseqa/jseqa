/**
 * The jseqa.alignment package provides pairwise sequence alignment classes and utilities. The types
 * of pairwise alignments supported out of the box are:
 * <br>
 *
 *  <table border="1">
 *   <tr>
 *       <td>Type</td>
 *       <td>Method</td>
 *       <td>Purpose</td>
 *   </tr>
 *   <tr>
 *       <td>Global</td>
 *       <td>{@link jseqa.alignment.GlobalAlignment}</td>
 *       <td>Aligns two full length sequences.</td>
 *   </tr>
 *   <tr>
 *       <td>Local</td>
 *       <td>{@link jseqa.alignment.LocalAlignment}</td>
 *       <td>Aligns the best scoring substrings.</td>
 *   </tr>
 *   <tr>
 *       <td>Overlap</td>
 *       <td>{@link jseqa.alignment.AffineOverlapAlignment}</td>
 *       <td>Global alignment without penalizing gaps on either end.</td>
 *   </tr>
 *   <tr>
 *       <td>Fitting</td>
 *       <td>{@link jseqa.alignment.FittingAlignment}</td>
 *       <td>Aligns a shorter sequence b completely fitting within larger sequence a.</td>
 *   </tr>
 * </table>
 * <br>
 * Additional types of alignments can be created by extending functionality of existing classes, namely
 * by changing initialization of matrix penalties, and where to select the cell to start backtracking.
 * <br><br>
 * There are different types of pairwise alignment:
 * <ul>
 *     <li>Basic: normal pairwise alignment with substitution/indel penalties.</li>
 *     <li>Affine: affine gap penalties, so that each gap can have a different gap start/gap extend penalty.</li>
 *     <li>Banded: Only allow aligning in a narrow band around the diagonal to speed up computation.</li>
 * </ul>
 *
 */
package jseqa.alignment;