#ifndef _TREE_H
#define _TREE_H

namespace tina {
	enum Token {

	};
	struct Type {

	};
	struct Node {

	};
	class Tree {
	public:
		Tree* createTree(int op, Type& type, Tree& left, Tree& right);
		Tree* expr(int k);
		void factor();
		int getToken();
		Tree* unary();
	private:
		int op;
		Type* type;
		Node node;
		Tree* kids[2];
		int t;
	};
}
#endif // !_TREE_H

