const { createYield } = require("typescript")

describe('Primeiro teste', () => {
  it('Exemplo cypress', () => {
    cy.visit('https://example.cypress.io')
    expect(true).to.equal(true)
  })
})
