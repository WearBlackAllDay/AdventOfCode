package wearblackallday.common;

public interface Grid {

	int width();

	int height();

	default void forAdjacent(int x, int y, IntBiConsumer xy) {
		if(this.inBounds(x + 1, y)) xy.accept(x + 1, y);
		if(this.inBounds(x - 1, y)) xy.accept(x - 1, y);

		if(this.inBounds(x + 1,y + 1)) xy.accept(x + 1, y + 1);
		if(this.inBounds(x,y + 1)) xy.accept(x, y + 1);
		if(this.inBounds(x - 1,y + 1)) xy.accept(x - 1, y + 1);

		if(this.inBounds(x + 1,y - 1)) xy.accept(x + 1, y - 1);
		if(this.inBounds(x, y - 1)) xy.accept(x, y - 1);
		if(this.inBounds(x - 1,y - 1)) xy.accept(x - 1, y - 1);
	}

	default void forAdjoining(int x, int y, IntBiConsumer xy) {
		if(this.inBounds(x + 1,y)) xy.accept(x + 1, y);
		if(this.inBounds(x - 1,y)) xy.accept(x - 1, y);

		if(this.inBounds(x,y + 1)) xy.accept(x, y + 1);
		if(this.inBounds(x,y - 1)) xy.accept(x, y - 1);
	}

	default void forEach(IntBiConsumer xy) {
		for(int x = 0; x < this.width(); x++) {
			for(int y = 0; y < this.height(); y++) {
				xy.accept(x, y);
			}
		}
	}

	default boolean inBounds(int x, int y) {
		return x >= 0 && x < this.width() && y >= 0 && y < this.height();
	}

	default int toIndex(int x, int y) {
		return x + this.width() * y;
	}

	default int size() {
		return this.width() * this.height();
	}

	@FunctionalInterface
	interface IntBiConsumer {
		void accept(int x, int y);
	}
}