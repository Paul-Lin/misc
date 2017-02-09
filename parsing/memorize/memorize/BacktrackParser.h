#ifndef _BACKTRACK_PARSER
#define _BACKTRACK_PARSER
#include <string>
#include "BacktrackLexer.h"
#include <vector>
#include <map>

namespace tina {
	class BacktrackParser {
	public:
		BacktrackParser(BacktrackLexer*);

		void stat();

		bool match(int type);

		void consume();

		void list();

		int type(int );

		Token* token(int);

		int mark();

		void sync(int );

		void fill(int);

		void release();
	private:
		BacktrackLexer* lexer;
		size_t marker = 0;
		
		std::vector<Token*>* lookahead;
		std::vector<int>* markers;
	};
}
#endif // !_BACKTRACK_PARSER

