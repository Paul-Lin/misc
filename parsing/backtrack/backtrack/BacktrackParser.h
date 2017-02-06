#ifndef _BACKTRACK_PARSER_H
#define _BACKTRACK_PARSER_H
#include "BacktrackLexer.h"
#include <vector>
namespace t {
	class BacktrackParser {
	public:
		BacktrackParser(BacktrackLexer* le);
		void mark();
		void release();
		Token* LT(int i);
		int LA(int i);
		void consume();
		bool match(int i);
		void stat();
		bool assign();
		bool list();
		bool elements();
		bool element();
		void sync(int i);
		
		bool specluate_rule_1();

		bool speculate_rule_2();
	private:
		BacktrackLexer* lexer;
		std::vector<int> *markers;
		std::vector<Token*> *lookahead;
		size_t p = 0;
	};
}
#endif // !_BACKTRACK_PARSER_H

