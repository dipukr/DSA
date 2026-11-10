package maven;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

enum Opcode {
	NOP,
	PUSH,
	PUSHN,
	POP,
	POPN,
	DUP,
	COPY,
	SAVE,
	LD_NULL,
	LD_TRUE,
	LD_FALSE,
	LOAD,
	LOADA,
	LOADM,
	STORE,
	STOREA,
	STOREM,
	NEWA,
	NEG,
	INC,
	DEC,
	ADD,
	SUB,
	MUL,
	DIV,
	MOD,
	SHL,
	SHR,
	BOR,
	BAND,
	BXOR,
	BNOT,
	CEQ,
	CNE,
	CLT,
	CLE,
	CGT,
	CGE,
	OR,
	AND,
	NOT,
	JUMP,
	JIT,
	JIF,
	CALL,
	RET,
	POS,
	ASSERT,
	COUT,
	HALT
}

enum Token {
	EOT,
	DOT,
	COMMA,
	COLON,
	BICOLON,
	SEMICOLON,
	QUESTION,
	LPAREN,
	RPAREN,
	LBRACE,
	RBRACE,
	LBRACKET,
	RBRACKET,
	ASSIGN,
	ADDASS,
	SUBASS,
	PLUS,
	MINUS,
	MULT,
	DIV,
	MOD,
	SHL,
	SHR,
	BOR,
	BAND,
	BXOR,
	BNOT,
	CEQ,
	CNE,
	CLT,
	CLE,
	CGT,
	CGE,
	OR,
	AND,
	NOT,
	VAL,
	VAR,
	CLASS,
	THIS,
	FUNCTION,
	DO,
	END,
	IF,
	ELSE,
	WHILE,
	FOREACH,
	BREAK,
	RETURN,
	NULL,
	TRUE,
	FALSE
}

enum Expression {
	Literal("Value value"),
	Identifier("String name"),
	Negation("Expression *expression"),
	Addition("Expression *left;Expression *right"),
	Subtract("Expression *left;Expression *right"),
	Multiply("Expression *left;Expression *right"),
	Division("Expression *left;Expression *right"),
	Modulus("Expression *left;Expression *right"),
	ShiftLeft("Expression *left;Expression *right"),
	ShiftRight("Expression *left;Expression *right"),
	BitwiseOR("Expression *left;Expression *right"),
	BitwiseAND("Expression *left;Expression *right"),
	BitwiseXOR("Expression *left;Expression *right"),
	BitwiseNOT("Expression *expression"),
	Equal("Expression *left;Expression *right"),
	NotEqual("Expression *left;Expression *right"),
	LessThan("Expression *left;Expression *right"),
	LessEqual("Expression *left;Expression *right"),
	GreaterThan("Expression *left;Expression *right"),
	GreaterEqual("Expression *left;Expression *right"),
	LogicalOR("Expression *left;Expression *right"),
	LogicalAND("Expression *left;Expression *right"),
	LogicalNOT("Expression *expression"),
	Conditional("Expression *condition;Expression *left;Expression *right"),
	Subscript("Expression *left;Expression *right"),
	Dot("Expression *left;Expression *right"),
	Call("Expression *caller;List<Expression*> *arguments"),
	AddAssign("Expression *left;Expression *right"),
	SubAssign("Expression *left;Expression *right"),
	Assignment("Expression *left;Expression *right");
	
	final String data;
	Expression(String data) {this.data = data;}
	public String getData() {return data;}
}

enum Statement {
	ClassStatement("List<Statement*> *variables;List<Statement*> *functions"),
	FunctionStatement("String name;List<String> *params;List<Statement*> *statements"),
	BlockStatement("List<Statement*> *statements"),
	VarStatement("String name;Expression *value"),
	ValStatement("String name;Expression *value"),
	IfStatement("Expression *condition;Statement *statement0;Statement *statement1"),
	WhileStatement("Expression *condition;Statement *statement"),
	ForeachStatement("String name;String target"),
	BreakStatement(""),
	ReturnStatement("Expression *value"),
	ExpressionStatement("Expression *expression");
	
	final String data;
	Statement(String data) {this.data = data;}
	public String getData() {return data;}
}

class Metadata {
	public String name;
	public String data;
	public String kind;
	public static Metadata of(String name, String data, String kind) {
		Metadata metadata = new Metadata();
		metadata.name = name;
		metadata.data = data;
		metadata.kind = kind;
		return metadata;
	}
}

public class Neem {
	private final String EXPR = "Expression";
	private final String STMT = "Statement";
	private Function<Expression, Metadata> emapper = (a) -> Metadata.of(a.name(), a.getData(), EXPR);
	private Function<Statement, Metadata> smapper = (a) -> Metadata.of(a.name(), a.getData(), STMT);
	private List<Metadata> expressions = Stream.of(Expression.values()).map(emapper).toList();
	private List<Metadata> statements = Stream.of(Statement.values()).map(smapper).toList();
	
	public void writeAstData(PrintWriter writer) throws Exception {
		writer.printf("struct Visitor;\n\n");
		writeClassDecls(writer, expressions);
		writeClassDecls(writer, statements);
		writeVisitor(writer);
		writeClassDefs(writer, expressions);
		writeClassDefs(writer, statements);
	}
	
	public void writeClassDecls(PrintWriter writer, List<Metadata> metadatas) throws Exception {
		final String kind = metadatas.get(0).kind;
		writer.printf("struct %s {\n", kind);
		writer.printf("\tuint pos;\n");
		writer.printf("\t%s(uint pos) : pos(pos) {}\n", kind);
		writer.printf("\tvirtual void accept(Visitor *visitor) = 0;\n");
		writer.printf("};\n\n");
		for (Metadata metadata: metadatas) {
			writer.printf("class %s: public %s\n{\n", metadata.name, kind);
			writeFields(writer, metadata);
			writer.printf("public:\n");
			writeConstructorDecl(writer, metadata);
			writeGettersDecl(writer, metadata);
			writeAcceptDecl(writer);
			writer.printf("};\n\n");
		}
	}
	
	public void writeClassDefs(PrintWriter writer, List<Metadata> metadatas) throws Exception {
		for (Metadata metadata: metadatas)
			writeConstructor(writer, metadata);
		writer.printf("\n");
		for (Metadata metadata: metadatas)
			writeGetters(writer, metadata);
			writer.printf("\n");
		for (Metadata metadata: metadatas)
			writeAccept(writer, metadata.name);
			writer.printf("\n");
	}
	
	public void writeFields(PrintWriter writer, Metadata data) throws Exception {
		String[] fields = data.data.split(";");
		if (notEmpty(fields)) {
			for (String field: fields) {
				String[] strs = field.split(" ");
				writer.printf("\t%s ", strs[0]);
				if (strs[1].charAt(0) == '*')
					writer.printf("*m_%s;\n", strs[1].substring(1));
				else writer.printf("m_%s;\n", strs[1]);
			}
		}
	}	
	
	public void writeConstructorDecl(PrintWriter writer, Metadata data) throws Exception {
		writer.printf("\t%s(", data.name);
		String[] fields = data.data.split(";");
		if (notEmpty(fields)) {
			for (int i = 0; i < fields.length; i++) {
				writer.printf("%s, ", fields[i]);
			}
		}
		writer.printf("uint pos);\n");
	}
	
	public void writeConstructor(PrintWriter writer, Metadata metadata) throws Exception {
		writer.printf("%s::%s(", metadata.name, metadata.name);
		String[] fields = metadata.data.split(";");
		if (notEmpty(fields)) {
			for (int i = 0; i < fields.length; i++) {
				writer.printf(fields[i]);
				writer.printf(", ");
			}
		}
		writer.printf("uint pos) : ");
		if (notEmpty(fields)) {
			for (int i = 0; i < fields.length; i++) {
				String field = fields[i];
				String[] strs = field.split(" ");
				String name = strs[1].charAt(0) == '*' ? strs[1].substring(1) : strs[1];
				writer.printf("m_%s(%s), ", name, name);
			}
		}
		writer.printf("%s(pos) {}\n", metadata.kind);
	}
	
	public void writeGettersDecl(PrintWriter writer, Metadata data) throws Exception {
		String[] fields = data.data.split(";");
		if (notEmpty(fields)) {
			for (String field: fields) {
				String[] strs = field.split(" ");
				if (strs[1].charAt(0) == '*')
					writer.printf("\t%s* %s() const;\n", strs[0], strs[1].substring(1));
				else writer.printf("\t%s %s() const;\n", strs[0], strs[1]);
			}
		}
	}
	
	public void writeGetters(PrintWriter writer, Metadata data) throws Exception {
		String[] fields = data.data.split(";");
		if (notEmpty(fields)) {
			for (String field: fields) {
				String[] strs = field.split(" ");
				if (strs[1].charAt(0) == '*')
					writer.printf("%s* %s::%s() const {return m_%s;}\n", strs[0], data.name, strs[1].substring(1), strs[1].substring(1));
				else writer.printf("%s %s::%s() const {return m_%s;}\n", strs[0], data.name, strs[1], strs[1]);
			}
		}
	}
	
	public void writeAcceptDecl(PrintWriter writer) throws Exception {
		writer.printf("\tvoid accept(Visitor *visitor);\n");
	}
	
	public void writeAccept(PrintWriter writer, String name) throws Exception {
		writer.printf("void %s::accept(Visitor *visitor) {visitor->visit(this);}\n", name);
	}
	
	public void writeVisitor(PrintWriter writer) throws Exception {
		writer.printf("struct Visitor {\n");
		for (Metadata metadata: expressions)
			writer.printf("\tvirtual void visit(%s *%s) = 0;\n", metadata.name, metadata.kind.toLowerCase());
		for (Metadata metadata: statements)
			writer.printf("\tvirtual void visit(%s *%s) = 0;\n", metadata.name, metadata.kind.toLowerCase());
		writer.printf("};\n");
	}
	
	public void writeVisitorImpl(PrintWriter writer, String name) throws Exception {
		writer.printf("class %s: public Visitor {\n", name);
		writer.printf("public:\n");
		
		writer.printf("};");
	}
	
	public boolean notEmpty(String[] fields) {
		if (fields.length == 1 && fields[0].isEmpty()) return false;
		return true;
	}
	
	public void writeAll(PrintWriter writer) throws Exception {
		
	}
	
	public static void main(String[] args) throws Exception {
		var neem = new Neem();
		File file = new File("output.data");
		FileWriter fileWriter = new FileWriter(file);
		PrintWriter writer = new PrintWriter(fileWriter, true);
		neem.writeAstData(writer);
		writer.flush();
		writer.close();
	}
}
