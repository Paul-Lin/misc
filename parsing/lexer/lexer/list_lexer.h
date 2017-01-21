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

		virtual Token* nextToken()=0;

		virtual std::string getTokenName(int tokenType)=0;

		static const int EOF_TYPE = 1;
		std::string input;
		size_t p = 0;
		char c;
	};
	class ListLexer : public Lexer{
	public:
		ListLexer(std::string& input) :Lexer(input) {}
		bool isLetter();
		tina::Token* nextToken();
		std::string getTokenName(int tokenType);

		static const int NAME = 2;
		static const int COMMA = 3;
		static const int LBRACK = 4;
		static const int RBRACK = 5;
		static const int EQUALS = 6;
		static const std::string tokenNames[];
	};
	class Parser {
	public:
		
		virtual void match(int x)=0;
		void consume();

		Lexer* lexer;
		Token* lookahead[2];
		int k;
		int peek = 0;
	};

	class ListParser:public Parser {
	public:
		ListParser(Lexer* input,int k){
			this->lexer = input;
			this->k = k;
			for (int i = 1; i <= k; i++)
				consume();
		}
		void match(int x);
		void list();
		void elements();
		void element();

		Token* LT(int i);

		int LA(int i);
	};
}
#endif // !_LIST_LEXER

