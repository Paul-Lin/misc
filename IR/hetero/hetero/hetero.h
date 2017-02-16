#ifndef _HETERO_H
#define _HETERO_H
#include <iostream>
#include <vector>
#include <string>

namespace tina {
	struct Token {
		const static int PLUS = 1;
		const static int INT = 2;

		int type;
		std::string text;
		Token(const int ty, const std::string& str) :type(ty), text(str) {}
		Token(const int ty):type(ty),text(nullptr){}
		std::string toString() { return text; }
	};

	class HeteroAst {
	public:
		HeteroAst(){}
		HeteroAst(Token* t);
		HeteroAst(int tokenType);

		virtual std::string toString();
		virtual std::string toStringTree();
	protected:
		Token* token;
	};
}
#endif // !_HETERO_H
