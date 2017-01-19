#ifndef _LIST_LEXER
#define _LIST_LEXER
#include <string>
#include "token.h"

namespace tina {
	class Lexer {
	public:
		Lexer(std::string& _input) :input(_input), c(_input.at(p)) {}

		void consume();

		void match(const char& x);

		virtual Token nextToken()=0;

		virtual std::string getTokenName(int tokenType)=0;

		static const int EOF_TYPE = 1;
		std::string input;
		size_t p = 0;
		char c;
	};
	class ListLexer : Lexer{
	public:
		ListLexer(std::string& input) :Lexer(input) {}
		std::string getTokenName(int x);
		bool isLetter();

		Token nextToken();

		static const int NAME = 2;
		static const int COMMA = 3;
		static const int LBRACK = 4;
		static const int RBRACK = 5;
		static const std::string tokenNames[];
	};

}
#endif // !_LIST_LEXER

