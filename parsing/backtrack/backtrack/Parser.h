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
		Lexer* input; // �ʷ���Ԫ����Դ
		std::vector<int> *markers; // ջ��������ڼ�¼λ�õı��
		std::vector<Token*> *lookahead; // ��С�ɱ�Ļ�����
		size_t p = 0; // ��ǰ��ǰ���ʷ���Ԫ���±�
		
	};
}
#endif

