#ifndef _HOMO_H
#define _HOMO_H
#include <vector>
#include <iostream>
#include <string>

namespace t {
	struct Token {
		const static int INVALIDATE_TYPE = -1;
		const static int PLUS = 1;
		const static int INT = 2;
		int type;
		std::string text;
		Token(){}
		Token(int ty,std::string txt):type(ty),text(txt){}
		std::string& toString() { return text; }
	};
	class Ast {
	public:
		Ast();
		Ast(Token* token);
		std::string toString();
		std::string toStringTree();
		inline bool isNil();
		void addChild(Ast* t);
	private:
		std::vector<Ast*> *children;
		Token* token;
	};
}
#endif // !_HOMO_H

