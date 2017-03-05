#ifndef _VISITORS_H
#include <iostream>
#include <vector>
#include <string>

namespace tina {
	class IntNode;
	class AddNode;
	class AssignNode;
	class VarNode;
	class VecMathVisitor {
	public:
		void visit(IntNode* node);
		void visit(AddNode *node);
		void visit(AssignNode *node);
		void visit(VarNode *node);
	};
	struct Token {
		const static int INVALID_TOKEN_TYPE = 0;
		const static int PLUS = 1;
		const static int MULT = 2;
		const static int DOT = 3;
		const static int INT = 4;
		const static int VEC = 5;
		const static int ID = 6;
		const static int ASSIGN = 7;
		const static int PRINT = 8;
		const static int STAT_LIST = 9;
		std::string* value;
		int type;

		Token(int ty,std::string *str):type(ty),value(str){}
		Token(int ty):type(ty),value(nullptr){}
		std::string toString() { return *value; }
	};
	class HeteroAst {
	public:
		Token *token;
		HeteroAst(){}
		HeteroAst(Token* t):token(t){}
		std::string toString() { return token->toString(); }
	private:
	};
	class VecMathNode :public HeteroAst {
	public:
		VecMathNode(){}
		VecMathNode(Token* t):HeteroAst(t){}
		virtual void print(VecMathVisitor *visitor) {
			std::cout << "vec math node" << std::endl;
		}
	};
	class ExprNode :public VecMathNode {
	public:
		ExprNode(Token *t):VecMathNode(t){}
		
	};
	class IntNode :public ExprNode {
	public:
		IntNode(Token *t):ExprNode(t){}
		void print(VecMathVisitor *visitor);
	};
	
	class VarNode :public ExprNode {
	public:
		VarNode(Token *t):ExprNode(t){}
		void print(VecMathVisitor *visitor) { visitor->visit(this); }
	};
	class AssignNode :public ExprNode {
	public:
		VarNode *id;
		AddNode *value;
		AssignNode(VarNode* i, Token *t, AddNode *v):id(i),ExprNode(t),value(v){}
		void print(VecMathVisitor *visitor) { visitor->visit(this); }
	};
	class AddNode :public ExprNode {
	public:
		ExprNode *left;
		ExprNode *right;
		AddNode(ExprNode *l,Token *t,ExprNode *r):left(l),ExprNode(t),right(r){}
	    void visit(VecMathVisitor *visitor) { visitor->visit(this); }
	};
	

}
#endif // !_VISITORS_H

