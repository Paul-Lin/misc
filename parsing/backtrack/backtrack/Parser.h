#ifndef _PARSER_H
#define _PARSER_H
#include "lexer.h"
#include <vector>
#include <iostream>

namespace tina {
	class Parser {
	public:
		Parser(Lexer* in);

		void sync(int i);

		void fill(int n);

		Token* LT(int i);

		int LA(int i);

		void match(int x)throw (std::string);

		void consume();

		int mark();

		void release();

		void seek(int index);

		bool isSpeculating();

		void stat();

		bool speculate_stat_alt1();

		bool speculate_stat_alt2();

		void list();

		void elements();

		void element();

		void assign();
	protected:
		Lexer* input; // 词法单元的来源
		std::vector<int> *markers; // 栈，存放用于记录位置的标记
		std::vector<Token*> *lookahead; // 大小可变的缓冲区
		size_t p = 0; // 当前向前看词法单元的下标
		
	};
}
#endif

