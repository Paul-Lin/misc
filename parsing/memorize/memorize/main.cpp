#include <iostream>
#include "BacktrackLexer.h"
#include "BacktrackParser.h"
#include "MemoryParser.h"

void func() {
	tina::BacktrackLexer* lexer = new tina::BacktrackLexer("[a,b]=[c,d]");
	tina::BacktrackParser* parser = new tina::BacktrackParser(lexer);
	parser->stat();
}

void func1() {
	tina::BacktrackLexer* lexer = new tina::BacktrackLexer("[a,b]=[c,d]");
	tina::MemoryParser* parser = new tina::MemoryParser(lexer);
	parser -> stat();
}

int main() {
	func1();
	system("pause");
	return 0;
}