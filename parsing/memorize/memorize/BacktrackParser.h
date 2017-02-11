#ifndef _BACKTRACK_PARSER
#define _BACKTRACK_PARSER
#include <string>
#include "BacktrackLexer.h"
#include <vector>
#include <map>
#include <exception>
namespace tina {
	class PreviousParsedFailedException : public std::exception {
	public:
		PreviousParsedFailedException() :exception("ERROR") {}
	};
	class BacktrackParser {
	public:
		BacktrackParser(BacktrackLexer*);

		void stat();

		bool match(int type);

		void consume();

		bool list();

		bool _list();

		int type(int);

		Token* token(int);

		int mark();

		bool elements();

		bool element();

		void sync(int);

		void fill(int);

		void release();

		bool assign();

		bool speculate_stat_alt1();

		bool speculate_stat_alt2();

		void memorize(std::map<int, int>* memorization, int startTokenIndex, bool failed);

		bool alreadyParsedRule(std::map<int, int>* memorization);
	private:
		BacktrackLexer* lexer;
		size_t marker = 0;
		static const int FAILED = -1;
		std::vector<Token*>* lookahead;
		std::vector<int>* markers;
		std::map<int, int>* listMemo = new std::map<int, int>();
	};
}
#endif // !_BACKTRACK_PARSER

