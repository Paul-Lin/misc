#ifndef _MEMORY_PARSER
#define _MEMORY_PARSER
#include <vector>
#include <map>
#include <iostream>
#include "BacktrackLexer.h"

namespace tina {
	class MemoryParser {
	public:
		MemoryParser(BacktrackLexer* input) :lexer(input) { sync(1); }
		
		int type(int index);

		Token* token(int index);

		void sync(int index);
		
		void fill(int size);

		bool match(int type);

		void stat();

		bool speculate_stat_alt1();

		bool speculate_stat_alt2();

		bool list();

		bool assign();

		bool _list();

		void mark();

		void release();

		bool alreadyParsedRule();

		bool elements();

		bool element();
	private:
		static const int FAILED = -1;
		BacktrackLexer* lexer;

		std::vector<Token*>* lookahead;
		std::vector<int>* markers;
		std::map<int, int> *memory = new std::map<int, int>();
		int marker;
		
	};
}
#endif // !_BACKTRACK_PARSER

