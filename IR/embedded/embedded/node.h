#include "hetero.h"

namespace tina {
	class VectNode :public HeteroAst {
		VectNode():HeteroAst(nullptr){}
		VectNode(Token* token):HeteroAst(token){}
		void print();
	};
	
}