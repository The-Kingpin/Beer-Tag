$(document).ready(function () {
    $('.nBtn , .table .eBtn').on('click',function(event) {
     event.preventDefault();

        var href = $(this).attr('href');

        var text = $(this).text();




        if (text === "Edit") {
            $.get(href, function (beer, status) {
                //$('.myForm #id').val(beer.id);
                $('.myForm #picture').val(beer.beerPicture);
                $('.myForm #name').val(beer.name);

                $('.myForm #abv').val(beer.alcoholByVolume);
                $('.myForm #brewery').val(beer.brewery);
               // $('.myForm #rating').val(beer.avgRating);
                $('.myForm #description').val(beer.description);
                $('.myForm #country').val(beer.country.name);
                $('.myForm #style').val(beer.beerStyle);

            });

            $('.container .myForm #exampleModal').modal();

        }else {
            //$('.myForm #id').val("");
            $('.myForm #id').val("");
            $('.myForm #picture').val("");
            $('.myForm #name').val("");
            $('.myForm #brewery').val("");
            $('.myForm #abv').val("");
            // $('.myForm #rating').val(beer.avgRating);
            $('.myForm #description').val("");
            $('.myForm #country').val("");
            $('.myForm #style').val("");
            // $('.myForm #rating').val("");

            $('.container .myForm #exampleModal').modal();
        }
        // }else if( text === "Details"){
        //         $.get(href, function (beer, status) {
        //             //$('.myForm #id').val(beer.id);
        //             $('.beerProfile #picture').val(beer.beerPicture);
        //             $('.beerProfile #name').val(beer.getName());
        //             $('.beerProfile #brewery').val(beer.brewery);
        //             $('.beerProfile #abv').val(beer.alcoholByVolume);
        //             // $('.myForm #rating').val(beer.avgRating);
        //             $('.beerProfile #description').val(beer.description);
        //             $('.beerProfile #country').val(beer.country.name);
        //             $('.beerProfile #style').val(beer.beerStyle);
        //
        //         });
        //
        //         $('.container #beerProfileModal').modal();
        // }
    });

    $('.table .dBtn').on('click', function () {
        event.preventDefault();
        var href = $(this).attr('href');

        $('#deleteModal #delRef').attr('href', href);
        $('#deleteModal').modal();
    });

});



// event.preventDefault();
//
// var href = $(this).attr('href');

// $.get(href, function (beer, status) {
//$('.myForm #id').val(beer.id);
// $('.exampleModal #name').val(beer.getBeerName);
// $('.exampleModal #brewery').val(beer.getBrewery);
// });