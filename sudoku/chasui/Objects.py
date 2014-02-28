__author__ = 'mike'

HORIZONTAL_DIMENSION = "horizontal"
VERTICAL_DIMENSION = "vertical"
BOX_DIMENSION = "box"


class Cell:
    def __init__(self):
        self._value = None
        self._dimensions = {}

    @property
    def value(self):
        return self._value

    @value.setter
    def value(self, value):
        self._value = value

    def set_dimension(self, dimension_type, dim):
        self._dimensions[dimension_type] = dim


class Dimension:
    def __init__(self, dimension_type):
        self._cells = []
        self._dimensionType = dimension_type

    @property
    def cells(self):
        return self._cells

    def add_cell(self, cell):
        if len(self._cells) == 9:
            raise Exception("c'mon bro. too many cells.")

        self._cells.append(cell)

    def get_available_values(self):
        return set(map(lambda c: c.value(), self._cells)).difference(range(1, 10))


def setup_relationships(horizontal_dimensions):
    for index in range(0, 9):
        pass






