pragma solidity >=0.8.0 <0.9.0;

//SPDX-License-Identifier: MIT

// import "hardhat/console.sol";

// import "@openzeppelin/contracts/access/Ownable.sol";
// https://github.com/OpenZeppelin/openzeppelin-contracts/blob/master/contracts/access/Ownable.sol

// contract YourContract {

//   event SetPurpose(address sender, string purpose);

//   string public purpose = "Building Unstoppable Apps!!!";

//   constructor() payable {
//     // what should we do on deploy?
//   }

//   function setPurpose(string memory newPurpose) public {
//       purpose = newPurpose;
//       console.log(msg.sender,"set purpose to",purpose);
//       emit SetPurpose(msg.sender, purpose);
//   }

//   // to support receiving ETH by default
//   receive() external payable {}
//   fallback() external payable {}
// }

contract RobotFactory {
    event NewRobot(
        uint256 robotId,
        string name,
        uint256 health,
        uint256 attack,
        uint256 robType
    );

    uint256 attributesDigits = 16;
    uint256 attributesModulus = 10**attributesDigits;

    struct Robot {
        string name;
        uint256 health;
        uint256 attack;
        uint256 robType;
    }

    Robot[] public robots;

    mapping(uint256 => address) public robotToOwner;
    mapping(address => uint256) ownerRobotCount;

    function _createRobot(
        string memory _name,
        uint256 _health,
        uint256 _attack,
        uint256 _robType
    ) internal {
        robots.push(Robot(_name, _health, _attack, _robType));
        uint256 id = robots.length - 1;
        robotToOwner[id] = msg.sender;
        ownerRobotCount[msg.sender]++;
        emit NewRobot(id, _name, _health, _attack, _robType);
    }

    uint256 randNonce = 0;

    function randMod(uint256 _modulus) internal returns (uint256) {
        // increase nonce
        randNonce++;
        return
            uint256(
                keccak256(
                    abi.encodePacked(block.timestamp, msg.sender, randNonce)
                )
            ) % _modulus;
    }

    function _generateAttributes()
        private
        returns (
            uint256 health,
            uint256 attack,
            uint256 robType
        )
    {
        health = randMod(7) + 1;
        attack = randMod(7);
        robType = randMod(6);
    }

    function createRandomRobot(string memory _name) public {
        require(ownerRobotCount[msg.sender] == 0);
        uint256 health;
        uint256 attack;
        uint256 robType;
        (health, attack, robType) = _generateAttributes();
        health = health - (health % 100);
        attack = attack - (attack % 100);
        robType = robType - (robType % 100);
        _createRobot(_name, health, attack, robType);
    }

    function getRobotsByOwner(address _owner)
        external
        view
        returns (uint256[] memory)
    {
        uint256[] memory result = new uint256[](ownerRobotCount[_owner]);
        uint256 counter = 0;
        for (uint256 i = 0; i < robots.length; i++) {
            if (robotToOwner[i] == _owner) {
                result[counter] = i;
                counter++;
            }
        }
        return result;
    }
}
