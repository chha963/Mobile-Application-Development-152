<?php
/**
 * Created by PhpStorm.
 * User: chha963
 * Date: 5/8/2016
 * Time: 2:54 PM
 */

namespace App;

use Illuminate\Database\Eloquent\Model;

class Product extends Model {

    /**
     * The table associated with the model.
     *
     * @var string
     */
    protected $table = 'product';

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'user_id', 'name', 'type', 'category_id', 'price', 'date_post', 'date_end',
        'brief_description', 'detai_description', 'icon', 'catalogue'
    ];

} 