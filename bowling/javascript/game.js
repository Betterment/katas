function peek(list) {
    if (list.length > 0) {
        return list[0];
    } else {
        return null;
    }
}
function peek2(list) {
    if (list.length > 1) {
        return list[1];
    } else {
        return null;
    }
}
function shift(list) {
    if (list.length > 0) {
        return list.shift();
    } else {
        return null;
    }
}
function push(list, item) {
    list.push(item);
    return list;
}

function isComplete(frame) {
    return isBonus(frame) ? frame.length === 3 : frame.length === 2;
}


function deepSum(item) {
    return item.reduce(function (total, value) {
        if (Array.isArray(value)) {
            value = deepSum(value);
        }
        return total + value;
    }, 0);
}

function buildFrames(rolls, frames) {
    if (rolls.length === 0 || Game.isComplete(frames)) {
        return frames;
    }

    rolls = rolls.slice();

    var frame = [shift(rolls)];

    if (!isBonus(frame)) {
        push(frame, shift(rolls));
    }

    if (isBonus(frame)) {
        push(frame, peek(rolls));
        if (frame.length < 3) {
            push(frame, peek2(rolls));
        }
    }

    frame = frame.filter(function (roll) {
        return roll !== null;
    });

    return buildFrames(rolls, push(frames, frame));
}

var Game = {
    isBonus: function isBonus(frame) {
        if (peek(frame) === 10) {
            return true;
        }

        if ((peek(frame) + peek2(frame)) === 10) {
            return true;
        }

        return false;
    },
    framesFromRolls: function framesFromRolls(rolls) {
        return buildFrames(rolls, []);
    },
    score: function (rolls) {
        return deepSum(framesFromRolls(rolls));
    },
    isComplete: function (frames) {
        return frames.length === 10 && isComplete(frames[9]);
    }
};

var isBonus = Game.isBonus;
var framesFromRolls = Game.framesFromRolls;

module.exports = exports = Game;
