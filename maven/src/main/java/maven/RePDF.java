package maven;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;

public class RePDF {
	public PDDocument replace(PDDocument document, String from, String to) throws Exception {
		for (PDPage page: document.getPages()) {
			PDFStreamParser parser = new PDFStreamParser(page);
			parser.parse();
			List<Object> tokens = parser.getTokens();
			for (int j = 0; j < tokens.size(); j++) {
				Object next = tokens.get(j);
				if (next instanceof Operator) {
					Operator op = (Operator) next;
					String pstring = "";
					int prej = 0;
					if (op.getName().equals("Tj")) {
						COSString previous = (COSString) tokens.get(j - 1);
						String string = previous.getString();
						string = string.replaceFirst(from, to);
						previous.setValue(string.getBytes());
					} else if (op.getName().equals("TJ")) {
						COSArray previous = (COSArray) tokens.get(j - 1);
						for (int k = 0; k < previous.size(); k++) {
							Object arrElement = previous.getObject(k);
							if (arrElement instanceof COSString) {
								COSString cosString = (COSString) arrElement;
								String string = cosString.getString();
								if (j == prej) {
									pstring += string;
								} else {
									prej = j;
									pstring = string;
								}
							}
						}
						if (from.equals(pstring.trim())) {
							COSString cosString2 = (COSString) previous.getObject(0);
							cosString2.setValue(to.getBytes());
							int total = previous.size() - 1;
							for (int k = total; k > 0; k--) {
								previous.remove(k);
							}
						}
					}
				}
			}
			PDStream updatedStream = new PDStream(document);
			OutputStream out = updatedStream.createOutputStream(COSName.FLATE_DECODE);
			ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
			tokenWriter.writeTokens(tokens);
			out.close();
			page.setContents(updatedStream);
		}
		return document;
	}
	
	public Map<String, String> getData() {
		var data = new TreeMap<String, String>();
		data.put("900000", "1100000");
		data.put("1000000", "1200000");
		data.put("450,000", "550,000");
		data.put("37,500", "45,833");
		data.put("225,000", "275,000");
		data.put("18,750", "22,500");
		data.put("37,485", "45,815");
		data.put("3,124", "3,818");
		data.put("138,745", "175,610");
		data.put("11,562", "14,634");
		data.put("851,230", "1,046,425");
		data.put("70,936", "87,202");
		data.put("951,230", "1,146,425.0");
		data.put("79,269", "95,535");
		data.put("21,650", "26,455");
		data.put("1,804", "2,205");
		data.put("48,770", "53,575");
		data.put("4,064", "4,465");
		data.put("1,000,000", "1,200,000");
		data.put("83,333", "100,000");
		return data;
	}
	
	public void main(String[] args) throws Exception {
		String fileName = "10LPA.pdf";
		String outFile = "12LPA.pdf";
		RePDF repl = new RePDF();
		
		PDDocument document = PDDocument.load(new File(fileName));
		
		if (document.isEncrypted()) {
			System.err.println("ERROR: PDF Documented is encrypted.");
			return;
		}
		for (Entry<String, String> elem: getData().entrySet()) {
			String from = elem.getKey();
			String to = elem.getValue();
			document = repl.replace(document, from, to);
		}
		document.save(outFile);
		document.close();
	}
}