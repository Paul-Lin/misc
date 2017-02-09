#ifndef _BACKTRACK_LEXER_H
#define _BACKTRACK_LEXER_H
#include <iostream>
#include <string>
namespace tina {
	struct Token {
		Token(int ty, const std::string& val) :type(ty), value(val) {}
		std::string value;
		int type;
		void toString();
	};
	class BacktrackLexer {
	public:
		static const int EOF_TYPE = 1;
		static const int COMMA = 2;
		static const int LBRACK = 3;
		static const int RBRACK = 4;
		static const int EQUALS = 5;
		static const int NAME = 6;

		static const std::string tokenNames[];
		
		BacktrackLexer(std::string in) :input(in) { ch = in.at(p); }

		void consume();

		bool isLetter();

		Token* name();
		Token* nextToken();
	private:
		std::string input;
		size_t p=0;
		char ch;
	};
}
#endif
