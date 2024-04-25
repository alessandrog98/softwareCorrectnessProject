package it.unive.golisa.cfg.runtime.shim.method;

import it.unive.golisa.cfg.expression.literal.GoTupleExpression;
import it.unive.golisa.cfg.runtime.shim.type.ChaincodeStub;
import it.unive.golisa.cfg.type.GoStringType;
import it.unive.golisa.cfg.type.composite.GoErrorType;
import it.unive.golisa.cfg.type.composite.GoSliceType;
import it.unive.golisa.cfg.type.composite.GoTupleType;
import it.unive.lisa.analysis.AbstractState;
import it.unive.lisa.analysis.AnalysisState;
import it.unive.lisa.analysis.SemanticException;
import it.unive.lisa.analysis.StatementStore;
import it.unive.lisa.interprocedural.InterproceduralAnalysis;
import it.unive.lisa.program.CompilationUnit;
import it.unive.lisa.program.annotations.Annotations;
import it.unive.lisa.program.cfg.CFG;
import it.unive.lisa.program.cfg.CodeLocation;
import it.unive.lisa.program.cfg.CodeMemberDescriptor;
import it.unive.lisa.program.cfg.NativeCFG;
import it.unive.lisa.program.cfg.Parameter;
import it.unive.lisa.program.cfg.statement.Expression;
import it.unive.lisa.program.cfg.statement.PluggableStatement;
import it.unive.lisa.program.cfg.statement.Statement;
import it.unive.lisa.symbolic.SymbolicExpression;
import it.unive.lisa.symbolic.heap.HeapDereference;
import it.unive.lisa.symbolic.value.Constant;
import it.unive.lisa.symbolic.value.MemoryPointer;
import it.unive.lisa.symbolic.value.TernaryExpression;
import it.unive.lisa.symbolic.value.operator.ternary.TernaryOperator;
import it.unive.lisa.type.Type;
import it.unive.lisa.type.TypeSystem;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * func (s *ChaincodeStub) CreateCompositeKey(objectType string, attributes
 * []string) (string, error).
 * 
 * @author <a href="mailto:vincenzo.arceri@unipr.it">Vincenzo Arceri</a>
 */
public class CreateCompositeKey extends NativeCFG {

	/**
	 * Builds the native cfg.
	 * 
	 * @param location the location where this native cfg is defined
	 * @param shimUnit the unit to which this native cfg belongs to
	 */
	public CreateCompositeKey(CodeLocation location, CompilationUnit shimUnit) {
		super(new CodeMemberDescriptor(location, shimUnit, true, "CreateCompositeKey",
				GoTupleType.getTupleTypeOf(location, GoStringType.INSTANCE,
						GoErrorType.INSTANCE),
				new Parameter(location, "this", ChaincodeStub.getChaincodeStubType(shimUnit.getProgram())),
				new Parameter(location, "objectType", GoStringType.INSTANCE),
				new Parameter(location, "attributes", GoSliceType.lookup(GoStringType.INSTANCE))),
				CreateCompositeKeyImpl.class);
	}

	/**
	 * The CreateCompositeKey implementation.
	 * 
	 * @author <a href="mailto:vincenzo.arceri@unipr.it">Vincenzo Arceri</a>
	 */
	public static class CreateCompositeKeyImpl extends it.unive.lisa.program.cfg.statement.TernaryExpression
			implements PluggableStatement {

		private Statement original;

		@Override
		public void setOriginatingStatement(Statement st) {
			original = st;
		}

		@Override
		protected int compareSameClassAndParams(Statement o) {
			return 0; // nothing else to compare
		}

		/**
		 * Builds the pluggable statement.
		 * 
		 * @param cfg      the {@link CFG} where this pluggable statement lies
		 * @param location the location where this pluggable statement is
		 *                     defined
		 * @param params   the parameters
		 * 
		 * @return the pluggable statement
		 */
		public static CreateCompositeKeyImpl build(CFG cfg, CodeLocation location, Expression... params) {
			return new CreateCompositeKeyImpl(cfg, location, params[0], params[1], params[2]);
		}

		/**
		 * Builds the pluggable statement.
		 * 
		 * @param cfg      the {@link CFG} where this pluggable statement lies
		 * @param location the location where this pluggable statement is
		 *                     defined
		 * @param params   the parameters
		 */
		public CreateCompositeKeyImpl(CFG cfg, CodeLocation location, Expression... params) {
			super(cfg, location, "CreateCompositeKeyImpl",
					GoTupleType.getTupleTypeOf(location, GoStringType.INSTANCE,
							GoErrorType.INSTANCE),
					params[0], params[1], params[2]);
		}

		@Override
		public <A extends AbstractState<A>> AnalysisState<A> fwdTernarySemantics(
				InterproceduralAnalysis<A> interprocedural, AnalysisState<A> state,
				SymbolicExpression left, SymbolicExpression middle, SymbolicExpression right,
				StatementStore<A> expressions) throws SemanticException {
			GoTupleType tupleType = GoTupleType.getTupleTypeOf(getLocation(), GoStringType.INSTANCE,
					GoErrorType.INSTANCE);
			AnalysisState<A> result = state.bottom();
			// Retrieves all the identifiers reachable from expr
			Collection<SymbolicExpression> reachableIds = state.getState().reachableFrom(left, this,
					state.getState()).elements;
			for (SymbolicExpression id : reachableIds) {
				if (id instanceof MemoryPointer)
					continue;
				Set<Type> idTypes = state.getState().getRuntimeTypesOf(id, this, state.getState());
				for (Type t : idTypes) {
					if (t.isPointerType()) {
						HeapDereference derefId = new HeapDereference(t.asPointerType().getInnerType(), id,
								getLocation());
						TernaryExpression leftExp = new TernaryExpression(GoStringType.INSTANCE,
								new Constant(getStaticType(), 1, getLocation()), middle,
								new Constant(getStaticType(), 1, getLocation()),
								CreateCompositeKeyOperatorFirstParameter.INSTANCE, getLocation());
						TernaryExpression rightExp = new TernaryExpression(GoErrorType.INSTANCE,
								new Constant(getStaticType(), 1, getLocation()), middle,
								new Constant(getStaticType(), 1, getLocation()),
								CreateCompositeKeyOperatorSecondParameter.INSTANCE, getLocation());
						AnalysisState<A> tupleState = GoTupleExpression.allocateTupleExpression(state,
								new Annotations(), original, getLocation(), tupleType,
								leftExp,
								rightExp);

						result = result.lub(tupleState);
					} else {
						TernaryExpression leftExp = new TernaryExpression(GoStringType.INSTANCE,
								new Constant(getStaticType(), 1, getLocation()), middle,
								new Constant(getStaticType(), 1, getLocation()),
								CreateCompositeKeyOperatorFirstParameter.INSTANCE, getLocation());
						TernaryExpression rightExp = new TernaryExpression(GoErrorType.INSTANCE,
								new Constant(getStaticType(), 1, getLocation()), middle,
								new Constant(getStaticType(), 1, getLocation()),
								CreateCompositeKeyOperatorSecondParameter.INSTANCE, getLocation());
						AnalysisState<A> tupleState = GoTupleExpression.allocateTupleExpression(state,
								new Annotations(), original, getLocation(), tupleType,
								leftExp,
								rightExp);

						result = result.lub(tupleState);
					}
				}
			}

			return result;
		}
	}

	/**
	 * The CreateCompositeKey operator returning the first parameter of the
	 * tuple expression result.
	 * 
	 * @author <a href="mailto:vincenzo.arceri@unipr.it">Vincenzo Arceri</a>
	 */
	public static class CreateCompositeKeyOperatorFirstParameter implements TernaryOperator {

		/**
		 * The singleton instance of this class.
		 */
		public static final CreateCompositeKeyOperatorFirstParameter INSTANCE = new CreateCompositeKeyOperatorFirstParameter();

		/**
		 * Builds the operator. This constructor is visible to allow
		 * subclassing: instances of this class should be unique, and the
		 * singleton can be retrieved through field {@link #INSTANCE}.
		 */
		protected CreateCompositeKeyOperatorFirstParameter() {
		}

		@Override
		public String toString() {
			return "CreateCompositeKeyOperator_1";
		}

		@Override
		public Set<Type> typeInference(TypeSystem types, Set<Type> left, Set<Type> middle, Set<Type> right) {
			return Collections.singleton(GoStringType.INSTANCE);
		}
	}

	/**
	 * The CreateCompositeKey operator returning the second parameter of the
	 * tuple expression result.
	 * 
	 * @author <a href="mailto:vincenzo.arceri@unipr.it">Vincenzo Arceri</a>
	 */
	public static class CreateCompositeKeyOperatorSecondParameter implements TernaryOperator {

		/**
		 * The singleton instance of this class.
		 */
		public static final CreateCompositeKeyOperatorSecondParameter INSTANCE = new CreateCompositeKeyOperatorSecondParameter();

		/**
		 * Builds the operator. This constructor is visible to allow
		 * subclassing: instances of this class should be unique, and the
		 * singleton can be retrieved through field {@link #INSTANCE}.
		 */
		protected CreateCompositeKeyOperatorSecondParameter() {
		}

		@Override
		public String toString() {
			return "CreateCompositeKeyOperator_2";
		}

		@Override
		public Set<Type> typeInference(TypeSystem types, Set<Type> left, Set<Type> middle, Set<Type> right) {
			return Collections.singleton(GoErrorType.INSTANCE);
		}
	}
}