contract RentProperty {

// =====================================================================================================================
// Variables
// =====================================================================================================================

    // Owner of this property
    address public owner;

    // The account currently renting the object
    address public renter;

    uint public rentalDate;
    uint public returnDate;

    // Dates when property is available to rent
    uint public availableFrom;
    uint public availableTo;

    // Property id
    uint public propertyId;

    // The price per second of the rental (in wei)
    uint public rentalPrice;

    // Whether or not the object is currently rented
    bool public rented;

// =====================================================================================================================
// Events
// =====================================================================================================================

   event E_Rent(address indexed _renter, uint _rentalDate, uint _returnDate, uint _rentalPrice);
   event E_ReturnRental(address indexed _renter, uint _returnDate);

// =====================================================================================================================
// Modifiers
// =====================================================================================================================

    /// @dev allows functions with this modifier to be called only when NOT rented
    modifier whenNotRented() {
        require(!rented);
        _;
    }

    /// @dev allows functions with this modifier to be called only when NOT rented
    modifier whenRented() {
        require(rented);
        _;
    }

// =====================================================================================================================
// Functions
// =====================================================================================================================


    function RentProperty(uint _availableFrom, uint _availableTo,
                          uint _propertyId, uint _rentalPrice) public {
        owner = msg.sender;
        availableFrom = _availableFrom;
        availableTo = _availableTo;
        propertyId = _propertyId;
        rentalPrice = _rentalPrice;
        rented = false;
    }

    /// @dev rents the object for given time
    function rent(uint _startRentalDate, uint _endRentalDate) public payable whenNotRented{
        require (msg.value > 0);
        require (_startRentalDate >= availableFrom && _endRentalDate <= availableTo);

        rented = true;
        renter = msg.sender;
        rentalDate = _startRentalDate;
        returnDate = _endRentalDate;
        uint multiplier = ((returnDate - rentalDate) / (1000 * 60 * 60 * 24));

        owner.transfer(rentalPrice * multiplier);

        E_Rent(renter, rentalDate, returnDate, rentalPrice * multiplier);
    }

    function endRentProperty () public whenRented {
        require ((owner == msg.sender && now > returnDate) || msg.sender == renter);
        require (rented);

        E_ReturnRental(renter, now);
        resetRental();

    }

    /// @dev resets the rental variables
    function resetRental() private{
        rented = false;
        renter = address(0);
        rentalDate = 0;
        returnDate = 0;
    }

}