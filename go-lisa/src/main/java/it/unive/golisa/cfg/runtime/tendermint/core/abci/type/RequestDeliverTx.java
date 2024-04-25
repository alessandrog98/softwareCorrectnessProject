package it.unive.golisa.cfg.runtime.tendermint.core.abci.type;

import it.unive.golisa.cfg.type.composite.GoStructType;
import it.unive.golisa.golang.util.GoLangUtils;
import it.unive.lisa.program.ClassUnit;
import it.unive.lisa.program.CompilationUnit;
import it.unive.lisa.program.Program;

/**
 * A Request of DeliverTx type.
 * 
 * @link https://pkg.go.dev/github.com/tendermint/tendermint/abci/types#DeliverTx
 * 
 * @author <a href="mailto:luca.olivieri@univr.it">Luca Olivieri</a>
 */
public class RequestDeliverTx extends GoStructType {

	/**
	 * Unique instance of the {@link RequestDeliverTx} type.
	 */
	public static RequestDeliverTx INSTANCE;

	private RequestDeliverTx(CompilationUnit unit) {
		super("RequestDeliverTx", unit);
	}

	/**
	 * Yields the {@link RequestDeliverTx} type.
	 * 
	 * @param program the program to which this type belongs
	 * 
	 * @return the {@link RequestDeliverTx} type
	 */
	public static RequestDeliverTx getRequestDeliverTxType(Program program) {
		if (INSTANCE == null) {
			ClassUnit abciUnit = new ClassUnit(GoLangUtils.GO_RUNTIME_SOURCECODE_LOCATION, program, "RequestDeliverTx",
					false);
			return new RequestDeliverTx(abciUnit);
		}

		return INSTANCE;
	}

	@Override
	public String toString() {
		return "abci.types.RequestDeliverTx";
	}

	@Override
	public boolean equals(Object other) {
		return this == other;
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}
}