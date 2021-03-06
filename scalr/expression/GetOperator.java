
package scalr.expression;

import scalr.variable.Note;
import scalr.variable.ScalrNum;
import scalr.variable.Sequence;

public class GetOperator implements Expression
{
	private Expression	   operand;
	private String	operator;

	public GetOperator(String op)
	{
		operator = op;
	}
	public void addOperand(Expression expr)
	{
		operand = expr;
	}

	@Override
	public Expression getValue()
	{
		// Type Checking
		if (operator.equals("dur")
		        && (operand.getType() != ExpressionType.SEQUENCE && operand.getType() != ExpressionType.NOTE)) {
			System.err.println("The duration operator is only valid for notes and sequences.");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println("\t" + elem);
			System.exit(1);
		}
		else if (operator.equals("pit") && operand.getType() != ExpressionType.NOTE) {
			System.err.println("The pitch operator is only valid for notes.");
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();
			for (StackTraceElement elem : stack)
				System.err.println("\t" + elem);
			System.exit(1);
		}
		if (operator.equals("dur")) {
			if (operand.getType() == ExpressionType.SEQUENCE) {
				Sequence seq = (Sequence) operand.getValue();
				return new ScalrNum(seq.getSequence().size());
			}
			else if (operand.getType() == ExpressionType.NOTE) {
				Note note = (Note) operand.getValue();
				return new ScalrNum(note.length.ordinal());
			}
		}
		else if (operator.equals("pit")) {
			if (operand.getType() == ExpressionType.NOTE) {
				Note note = (Note) operand.getValue();
				return new ScalrNum(note.pitch.ordinal());
			}
		}
		else if (operator.equals("vol")) {
			if (operand.getType() == ExpressionType.NOTE) {
				Note note = (Note) operand.getValue();
				return new ScalrNum(note.volume);
			}
		}
		// Someone used this function inappropriately.
		return null;
	}

	@Override
	public ExpressionType getType()
	{
		if (operator.equals("dur")) {
			if (operand.getType() == ExpressionType.NOTE
			        || operand.getType() == ExpressionType.SEQUENCE)
				return ExpressionType.NUMBER;
		}
		else if (operator.equals("pit")) {
			if (operand.getType() == ExpressionType.NOTE)
				return ExpressionType.NUMBER;
		}
		else if (operator.equals("vol")) {
			if (operand.getType() == ExpressionType.NOTE)
				return ExpressionType.NUMBER;
		}
		return null;
	}

}
