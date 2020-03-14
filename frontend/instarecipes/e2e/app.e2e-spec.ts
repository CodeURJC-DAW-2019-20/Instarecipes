import { InstarecipesPage } from './app.po';

describe('instarecipes App', function() {
  let page: InstarecipesPage;

  beforeEach(() => {
    page = new InstarecipesPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
