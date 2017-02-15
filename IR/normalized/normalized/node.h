#include "ast.h"

namespace tina {
	class ExprNode :public Ast {
	public:
		const static int T_INVALID = 0;
		const static int T_INTEGER = 1;
		const static int T_VECTOR = 2;

		inline int getEvalType() { return evalType; }
		ExprNode(Token* payload):Ast(payload){}
		std::string toString();
	protected:
		int evalType;
	};

	class AddNode :public ExprNode {
	public:
		inline int getEvalType();
		AddNode(ExprNode* left, Token* addToken, ExprNode* right);
	};

	class IntNode :public ExprNode {
	public:
		IntNode(Token* t) :ExprNode(t){
			ExprNode::evalType=ExprNode::T_INTEGER;
		}
	};

	class VectorNode :public ExprNode {
	public:
		VectorNode(Token* t, std::vector<ExprNode*> elements);
	};
}

