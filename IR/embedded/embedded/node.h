#include "hetero.h"

namespace tina {
	class VectMathNode :public HeteroAst {
	public:
		VectMathNode():HeteroAst(nullptr){}
		VectMathNode(Token* token):HeteroAst(token){}
		virtual void print();
	};
	class StatNode :public VectMathNode {
	public:
		StatNode(){}
		StatNode(Token* token):VectMathNode(token){}
	};
	class ExprNode :public VectMathNode {
	public:
		ExprNode(Token* token):VectMathNode(token){}
	};
	class VarNode :public ExprNode {
	public:
		VarNode(Token* token):ExprNode(token){}
	};
	class IntNode :public ExprNode {
	public:
		IntNode(Token* token):ExprNode(token){}
	};
	class AddNode :public ExprNode {
	public:
		ExprNode* left;
		ExprNode* right;
		AddNode(ExprNode* vleft,Token *vt,ExprNode *vright):left(vleft),ExprNode(vt),right(vright){}
		~AddNode() { delete left; delete right; }
		void print();
	};
	class AssignNode :public StatNode {
	public:
		VectMathNode* id;
		ExprNode* value;
		AssignNode(VarNode* vid,Token* vtoken,ExprNode* vvalue):id(vid),StatNode(vtoken),value(vvalue){}
		void print();
		~AssignNode() { delete id; delete value; }
	};
}