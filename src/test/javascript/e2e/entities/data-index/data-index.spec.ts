import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/sim-page-objects';
import { DataIndexComponentsPage, DataIndexUpdatePage } from './data-index.page-object';

describe('DataIndex e2e test', () => {
    let navBarPage: NavBarPage;
    let dataIndexUpdatePage: DataIndexUpdatePage;
    let dataIndexComponentsPage: DataIndexComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load DataIndices', () => {
        navBarPage.goToEntity('data-index');
        dataIndexComponentsPage = new DataIndexComponentsPage();
        expect(dataIndexComponentsPage.getTitle()).toMatch(/Data Indices/);
    });

    it('should load create DataIndex page', () => {
        dataIndexComponentsPage.clickOnCreateButton();
        dataIndexUpdatePage = new DataIndexUpdatePage();
        expect(dataIndexUpdatePage.getPageTitle()).toMatch(/Create or edit a Data Index/);
        dataIndexUpdatePage.cancel();
    });

    it('should create and save DataIndices', () => {
        dataIndexComponentsPage.clickOnCreateButton();
        dataIndexUpdatePage.setIndexIDInput('indexID');
        expect(dataIndexUpdatePage.getIndexIDInput()).toMatch('indexID');
        dataIndexUpdatePage.setIndexByInput('indexBy');
        expect(dataIndexUpdatePage.getIndexByInput()).toMatch('indexBy');
        dataIndexUpdatePage.save();
        expect(dataIndexUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
