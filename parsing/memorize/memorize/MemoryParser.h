#ifndef _MEMORY_PARSER
#define _MEMORY_PARSER
#include <vector>
#include <map>
#include <iostream>
#include "BacktrackLexer.h"

namespace tina {
	class MemoryParser {
	public:
		MemoryParser(BacktrackLexer* input);
		
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

		void memorize(int startTokenIndex,bool failed);

		bool elements();

		bool element();

		void consume();
	private:
		static const int FAILED = -1;
		BacktrackLexer* lexer;

		std::vector<Token*>* lookahead;
		std::vector<int>* markers;
		std::map<int, int> *memory = new std::map<int, int>();
		int marker=0;
		
	};
}
#endif // !_BACKTRACK_PARSER

