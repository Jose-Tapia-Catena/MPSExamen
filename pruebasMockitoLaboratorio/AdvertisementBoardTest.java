package org.cate.noticeboard;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;


class AdvertisementBoardTest {

    @Test
    public void ABoardHasAnAdvertisementWhenItIsCreated() {
        AdvertisementBoard ab = new AdvertisementBoard();

        assertThat(ab.numberOfPublishedAdvertisements()).isEqualTo(1);
    }

    @Test
    public void PublishAnAdvertisementByTheCompanyIncreasesTheNumberOfAdvertisementsByOne() {
        AdvertisementBoard ab = new AdvertisementBoard();
        Advertisement ad1 = new Advertisement(
                "Advertisement Title",
                "Advertisement text",
                "THE Company");

        int size = ab.numberOfPublishedAdvertisements();

        //Creamos objeto Mockito
        AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
        PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);

        ab.publish(ad1,advertiserDatabase,paymentDatabase);

        assertThat(ab.numberOfPublishedAdvertisements()).isEqualTo(size+1);
    }

    @Test
    public void WhenAnAdvertiserHasNoFoundsTheAdvertisementIsNotPublished() {
        AdvertisementBoard ab = new AdvertisementBoard();

        Advertisement ad1 = new Advertisement(
                "Advertisement Title",
                "Advertisement text",
                "Pepe Gotera y Otilio");

        int size = ab.numberOfPublishedAdvertisements();

        //Creamos objeto Mockito
        AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
        PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);

        //Definimos comportamiento
        Mockito.when(advertiserDatabase.findAdviser(ad1.advertiser)).thenReturn(true);
        Mockito.when(paymentDatabase.advertiserHasFunds(ad1.advertiser)).thenReturn(false);

        ab.publish(ad1,advertiserDatabase,paymentDatabase);

        assertThat(ab.numberOfPublishedAdvertisements()).isEqualTo(size);
    }

    @Test
    public void AnAdvertisementIsPublishedIfTheAdvertiserIsRegisteredAndHasFunds() {
        AdvertisementBoard ab = new AdvertisementBoard();

        Advertisement ad1 = new Advertisement(
                "Advertisement Title",
                "Advertisement text",
                "Robin Robot");

        //Creamos objeto Mockito
        AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
        PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);

        //Definimos comportamiento
        Mockito.when(advertiserDatabase.findAdviser(ad1.advertiser)).thenReturn(true);
        Mockito.when(paymentDatabase.advertiserHasFunds(ad1.advertiser)).thenReturn(true);

        //Ejecutamos
        ab.publish(ad1,advertiserDatabase,paymentDatabase);

        //Verify
        Mockito.verify(paymentDatabase).advertisementPublished(ad1.advertiser);
        Mockito.verify(paymentDatabase, Mockito.times(1)).advertisementPublished(ad1.advertiser);

    }

    @Test
    public void WhenTheOwnerMakesTwoPublicationsAndTheFirstOneIsDeletedItIsNotInTheBoard() {
        AdvertisementBoard ab = new AdvertisementBoard();
        Advertisement ad1 = new Advertisement(
                "Advertisement Title",
                "Advertisement text",
                "THE Company");

        Advertisement ad2 = new Advertisement(
                "Advertisement2 Title",
                "Advertisement2 text",
                "THE Company");

        //Creamos objeto Mockito
        AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
        PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);

        //Ejecutamos
        ab.publish(ad1,advertiserDatabase,paymentDatabase);
        ab.publish(ad2,advertiserDatabase,paymentDatabase);

        ab.deleteAdvertisement(ad1.title,ad1.advertiser);

        assertThat(ab.findByTitle(ad1.title)).isNull();
    }

    @Test
    public void AnExistingAdvertisementIsNotPublished() {
        AdvertisementBoard ab = new AdvertisementBoard();
        String advertiser = "Jose Maria";

        Advertisement ad1 = new Advertisement(
                "Advertisement Title",
                "Advertisement text",
                advertiser);

        Advertisement ad2 = new Advertisement(
                "Advertisement Title",
                "Advertisement text",
                advertiser);

        //Creamos objeto Mockito
        AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
        PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);

        //Definimos comportamiento
        Mockito.when(advertiserDatabase.findAdviser(advertiser)).thenReturn(true);
        Mockito.when(paymentDatabase.advertiserHasFunds(advertiser)).thenReturn(true);

        //Ejecutamos
        ab.publish(ad1,advertiserDatabase,paymentDatabase);
        ab.publish(ad2,advertiserDatabase,paymentDatabase);

        //Verify
        Mockito.verify(paymentDatabase).advertisementPublished(advertiser);
        Mockito.verify(paymentDatabase, Mockito.times(1)).advertisementPublished(advertiser);
    }

    @Test
    public void AnExceptionIsRaisedIfTheBoardIsFullAndANewAdvertisementIsPublished() {
        AdvertisementBoard ab = Mockito.spy(AdvertisementBoard.class);

        Advertisement ad1 = new Advertisement(
                "Advertisement Title",
                "Advertisement text",
                "Tim O'Theo");

        //Creamos objeto Mockito
        AdvertiserDatabase advertiserDatabase = Mockito.mock(AdvertiserDatabase.class);
        PaymentDatabase paymentDatabase = Mockito.mock(PaymentDatabase.class);

        Mockito.doReturn(ab.getMaxBoardSize()).when(ab).numberOfPublishedAdvertisements();

        assertThrows(FullBoardException.class, ()-> ab.publish(ad1, advertiserDatabase, paymentDatabase));
    }
}