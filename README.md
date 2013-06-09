#Item Exchange

Item Exchange is a minecraft mod that enables different inventory blocks to perform a predefined exchange of items. The exchange which an inventory block is capable of is defined by the exchange rule items contained within the inventory block. An exchange rule item is an item generated by Item Exchange which contains information detailing either the input or output item stack of an exchange. For an inventory block to perform an exchange it must contain both an input exchange rule item and an output exchange rule item, if it does when a player left clicks it while holding the correct input item type an exchange will be conducted.

##Commands

###/iecreate (or /iec) [input or output] [common name or ID:durability] [amount]

This creates the exchange rule items required for an item exchange to function, the command has multiple functionalities:
-  If used when a player is looking at an acceptable inventory block it will attempt to create exchange rule items within that inventory block. The inventory block must contain only two different kind of items, although these items may be present in multiple stacks. The first item present in the inventory is assumed to be the player input and the second item the inventory block output.
-  If an input or output is specified this command will create an item exchange rule that represents the item stack held in the players hand and place it in the players inventory.
-  If common name* or a material ID:durability is specified in addition to an input or output an exchange rule item is generated within the players inventory representing the specified ItemStack with an amount of one.
-  If an amount is specified this command performs similiar to the one above except with an amount set as specified.

###/ieset (or /ies) <field> [value] [modifier]

This command allows you to change specific values of the exchange rule item held in the players hand.

The fields which may be set with this command are as follows:
-  commonname
-  material
-  durability
-  amount
-  enchantment
-  displayname
-  lore
-  switchio

For all fields except switch io a value must be specified. switchio simply toggles the exchange rule between an input and output rule. enchantment must have also specify a modifier of "required" or "excluded", this specifies whether the enchantment is required for the exchange or prohibited from the exchange, enchantments not included in either of these catagories are ignored during exchanges. 

##Other Notes
-  To increase usability where possible the english displayed name (referred to as "common name") is used in place of the bukkit material name and durability value.
-  Current accepted inventory blocks are chests, double chests, dispensors and trapped chests. Nothing prohibits additional inventory blocks from being added except for testing time.