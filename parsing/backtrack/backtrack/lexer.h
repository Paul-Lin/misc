#ifndef _LEXER_H
#define _LEXER_H
#include <string>
#include <exception>

namespace tina {
	
	struct Token {
		int type;
		std::string text;
		Token(int ty, std::string& txt) :type(ty), text(txt) {}
		std::string& toString();
	};
	class Lexer {
	public:
		static const int EOF_TYPE = 1;
		static const int NAME = 2;
		static const int COMMA = 3;
		static const int LBRACK = 4;
		static const int RBRACK = 5;
		static const int EQUALS = 6;
		static std::string tokenNames[];
		Lexer(std::string& in) :input(in) {
			c = input.at(i);
		}
		void consume();

		void advance();

		void match(char x);

		bool isLetter();

		Token* nextToken();
		Token* name();
	private:
		std::string input;
		size_t i = 0;
		char c;
	};
}
#endif // !_LEXER_H
