const utils = require("./utils")
// @ponicode
describe("utils.root", () => {
    test("0", () => {
        let callFunction = () => {
            utils.root([-5.48, 100, 1, -5.48, 1, -100, -100])
        }
    
        expect(callFunction).not.toThrow()
    })

    test("1", () => {
        let callFunction = () => {
            utils.root([-5.48, -100, 1, 0, -100, 0, 1])
        }
    
        expect(callFunction).not.toThrow()
    })

    test("2", () => {
        let callFunction = () => {
            utils.root([100, -5.48, -100, 0, 100, 0, 100])
        }
    
        expect(callFunction).not.toThrow()
    })

    test("3", () => {
        let callFunction = () => {
            utils.root([1, -100, 0, -5.48, -100, 1, 0])
        }
    
        expect(callFunction).not.toThrow()
    })

    test("4", () => {
        let callFunction = () => {
            utils.root([0, -5.48, 100, -100, 100, -100, 0])
        }
    
        expect(callFunction).not.toThrow()
    })

    test("5", () => {
        let callFunction = () => {
            utils.root(undefined)
        }
    
        expect(callFunction).not.toThrow()
    })
})
